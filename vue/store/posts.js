import Vue from 'vue';
export const state = () => ({
    mainPosts: [],
    hasMorePost: true,
});


const limit = 5;
let pagination = 0;
export const mutations = {
    addMainPost(state, payload) {
        state.mainPosts.unshift(payload); //최신 게시글을 맨 앞에 보여줄 것이므로 push가 아닌 unshift
    },
    modifyPost(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        console.log("modify index ", index)
        state.mainPosts[index].title = payload.title;
        state.mainPosts[index].content = payload.content;
    },
    removeMainPost(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        state.mainPosts[index].title = "삭제된 게시글 입니다.";
        // state.mainPosts.splice(index, 1);
    },
    removeComment(state, payload) {
        const postIndex = state.mainPosts.findIndex(v => v.postId === payload.postId);
        console.log("postIndex =>", postIndex);
        const commentsIndex = state.mainPosts[postIndex].Comments.findIndex(v => v.commentsId === payload.commentsId);
        console.log("commentsIndex =>", commentsIndex);
        state.mainPosts[postIndex].Comments[commentsIndex].comments = "삭제된 댓글 입니다.";
    },
    addComment(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        console.log("postId", payload.postId);
        console.log(index);
        console.log("payload", payload);
        console.log(state.mainPosts[index]);
        state.mainPosts[index].commentsLength +=1;
        state.mainPosts[index].Comments.push(payload);

    },
    loadPosts(state, payload) {
        state.mainPosts = state.mainPosts.concat(payload);
        state.hasMorePost = payload.length === limit;
    },
    loadComments(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        console.log("indexId",index);
        console.log("postId", payload.postId);
        Vue.set(state.mainPosts[index], 'Comments', payload.data);
        console.log("CommentsLength", payload.CommentsLength);
        Vue.set(state.mainPosts[index], 'CommentsLength', payload.CommentsLength);
    },

    

};

export const actions = {
    add({commit, dispatch}, payload) {
        // 서버에 게시글 등록 요청 보냄
        this.$axios.post('http://localhost:8080/post/free', {
                content: payload.content,
                email: payload.email,
                title: payload.title,
            },
        )
            .then((res) => {
                const Obj = JSON.stringify(res.data);
                const respObj = JSON.parse(Obj);
                console.log("성공!!!!!!!!!" + respObj.postId);
                commit('addMainPost', {
                    postId: respObj.postId,
                    email: respObj.email,
                    content: respObj.content,
                    title: respObj.title,
                    createdAt: respObj.createdAt,
                    commentsLength: respObj.commentsLength,
                    isDeleted: respObj.isDeleted,
                })
            })
            .catch((e) => {
                console.log("실패!!!!!!!!!" + e.getMessage);

            });
    },
    modifyPost({commit, dispatch}, payload) {
        let token = localStorage.getItem("authtoken");
        let config = {
            headers: {
                "authtoken": token,
            },
        };
        console.log("----------------------------", payload.postId)
        this.$axios.put(`http://localhost:8080/post/${payload.postId}`, {
                content: payload.content,
                email: payload.email,
                title: payload.title,
                commentsLength: payload.commentsLength,
                isDeleted: payload.isDeleted,
            },config
        )
            .then((res) => {
                const Obj = JSON.stringify(res.data);
                const respObj = JSON.parse(Obj);
                console.log("성공!!!!!!!!!" + Obj);
                commit('modifyPost', {
                    postId: payload.postId,
                    email: respObj.email,
                    content: respObj.content,
                    title: respObj.title,
                    createdAt: respObj.createdAt,
                    commentsLength: respObj.commentsLength,
                    isDeleted: respObj.isDeleted,
                })
                history.go(-1)
            })
            .catch((e) => {
                console.log("실패!!!!!!!!!" + e.getMessage);

            });

    },
    remove({commit}, payload) {
        if (localStorage.getItem("authtoken")) {

            let token = localStorage.getItem("authtoken");
            let config = {
                headers: {
                    "authtoken": token,
                },
            };
            this.$axios.delete(`http://localhost:8080/post/${payload.postId}`, config)
                .then((res) => {
                        commit('removeMainPost', payload)
                        console.log("delete 성공" + res.data)
                    }
                )
                .catch((err) => {
                    alert("글을 삭제할 수 없습니다.")
                    console.log(`http://localhost:8080/post/${payload.postId}`)
                })
            ;

        }
    }, // /removeComment
    removeComment({commit}, payload) {
        if (localStorage.getItem("authtoken")) {

            let token = localStorage.getItem("authtoken");
            let config = {
                headers: {
                    "authtoken": token,
                },
            };
            this.$axios.delete(`http://localhost:8080/comments/${payload.commentsId}`, config)
                .then((res) => {
                    console.log("removeComment payload.commentsId-> ", payload.commentsId)
                        commit('removeComment', payload)
                        console.log("delete 성공" + res.data)
                    }
                )
                .catch((err) => {
                    console.log(err)
                })
            ;

        }
    },
    addComment({commit}, payload) {
        let token = localStorage.getItem("authtoken");
        let config = {
            headers: {
                "authtoken": token,
            },
        };
        this.$axios.post(`http://localhost:8080/comments/${payload.postId}`, {
                parentsId: payload.parentsId,
                comments: payload.Comments,
                email: payload.User.nickname,
            }, config)
            .then((res) => {
                const Obj = JSON.stringify(res.data);
                const respObj = JSON.parse(Obj);
                console.log("addComment 성공!!!!!!!!!" + Obj);
                // commit('addComment', Obj);
                commit('addComment', {
                    parentsId: respObj.parentsId,
                    postId: respObj.post.postId,
                    comments: respObj.comments,
                    email: respObj.email,
                    commentsId: respObj.commentsId,
                    isDeleted: respObj.isDeleted,
                    createdAt: respObj.createdAt,
                });
                console.log("commit 성공");
            })
            .catch((err) => {
                console.log("commit err");
                console.log(err);
            });

        // commit('addComment', {
        //     Comments: payload.Comments,
        //     email: payload.User.nickname,
        // });
    },
    loadPosts({commit, state}, payload) {
        if (state.hasMorePost) {
            return this.$axios.get('http://localhost:8080/posts/free?size=5&sort=postId,DESC&page=' + pagination)
                .then((res) => {
                    pagination += 1;
                    const Obj = JSON.stringify(res.data);
                    const respObj = JSON.parse(Obj);
                    const json = respObj._embedded.postList;
                    commit('loadPosts', json);
                })
                .catch((err) => {
                    // console.error("loadPosts err catch! =>>   ", err);
                });

        }
    },
    loadComments({commit}, payload) {
        console.log("posts/loadComments호출")
        this.$axios.get(`http://localhost:8080/comments/${payload.postId}`)
            .then((res) => {
                console.log("comments 성공")
                const Obj = JSON.stringify(res.data);
                const respObj = JSON.parse(Obj);
                console.log("=========")
                console.log(respObj)
                commit('loadComments',
                    {
                        postId: payload.postId,
                        data:respObj,
                        CommentsLength: respObj.length,
                    });

            })
            .catch((err) => {
                console.log("comments 실패", err)
            })
    },
    uploadImages({commit}, payload) {
        this.$axios.post('http://localhost:8080/testUpload/1', {
            fileName: payload.fileName,
            fileType: payload.fileType,
            fileUrl: payload.fileUrl,
        }, {
            withCredentials: true,
        })
            .then((res) => {


            })
            .catch(() => {

            })

    },
};