import Vue from 'vue';
import throttle from 'lodash.throttle';

export const state = () => ({
    mainPosts: [],
    hasMorePost: true,
});


const limit = 5;
let pagination = -1;
export const mutations = {
    addMainPost(state, payload) {
        state.mainPosts.unshift(payload); //최신 게시글을 맨 앞에 보여줄 것이므로 push가 아닌 unshift
    },
    modifyPost(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        // console.log("modify index ", index)
        state.mainPosts[index].title = payload.title;
        state.mainPosts[index].content = payload.content;
    },
    removeMainPost(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        state.mainPosts[index].title = "삭제된 게시글 입니다.";
        state.mainPosts[index].fileCnt -=1;
        // state.mainPosts.splice(index, 1);
    },
    removeComment(state, payload) {
        const postIndex = state.mainPosts.findIndex(v => v.postId === payload.postId);
        // console.log("postIndex =>", postIndex);
        const commentsIndex = state.mainPosts[postIndex].Comments.findIndex(v => v.commentsId === payload.commentsId);
        // console.log("commentsIndex =>", commentsIndex);
        state.mainPosts[postIndex].Comments[commentsIndex].comments = "삭제된 댓글 입니다.";
    },
    deleteFile(state, payload) {
        const postIndex = state.mainPosts.findIndex(v => v.postId === payload.postId);
        // console.log("postIndex =>", postIndex);
        const commentsIndex = state.mainPosts[postIndex].uploadImages.findIndex(v => v.fileId === payload.fileId);
        // console.log("commentsIndex =>", commentsIndex);
        state.mainPosts[postIndex].uploadImages.splice(commentsIndex, 1);
    },
    addComment(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        // console.log("postId", payload.postId);
        // console.log(index);
        // console.log("payload", payload);
        // console.log(state.mainPosts[index]);
        state.mainPosts[index].commentsLength += 1;
        state.mainPosts[index].Comments.push(payload);

    },
    loadPosts(state, payload) {
        if (payload.reset) {
            state.mainPosts = payload.data;
        } else {
            state.mainPosts = state.mainPosts.concat(payload.data);
        }
        // console.log("length ============> ", payload.length);
        // state.mainPosts = state.mainPosts.concat(payload);
        state.hasMorePost = payload.data.length === limit;

    },
    loadComments(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        // console.log("indexId", index);
        // console.log("postId", payload.postId);
        Vue.set(state.mainPosts[index], 'Comments', payload.data);
        // console.log("CommentsLength", payload.CommentsLength);
        Vue.set(state.mainPosts[index], 'CommentsLength', payload.CommentsLength);
    },
    uploadImages(state, payload) {
        if (payload.postId !== null) {
            const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
            console.log(payload);
            console.log(payload.uri)
            // Vue.set(state.mainPosts[index], 'uploadImages', payload);
            Vue.set(state.mainPosts[index], 'uploadImages', []);
            state.mainPosts[index].uploadImages.push(payload);
            console.log("payload.fileName=", payload.fileName)
        } else {
            Vue.set(state.mainPosts, 'uploadImages', []);
            state.mainPosts.uploadImages.push(payload);
        }

    },
    getFiles(state, payload) {
        const postId = parseInt(payload.postId);
        const index = state.mainPosts.findIndex(v => v.postId === postId);
        console.log("index => " , index);
        Vue.set(state.mainPosts[index], 'uploadImages', []);
        state.mainPosts[index].uploadImages.push(payload);
    },


};

export const actions = {
    add({commit, dispatch}, payload) {
        // 서버에 게시글 등록 요청 보냄
        this.$axios.post('http://localhost:8080/post/free', {
                content: payload.content,
                email: payload.email,
                title: payload.title,
                fileId: payload.fileId,
            },
        )
            .then((res) => {
                const Obj = JSON.stringify(res.data);
                const respObj = JSON.parse(Obj);
                console.log("성공!!!!!!!!!" + JSON.stringify(respObj));
                commit('addMainPost', {
                    _links: respObj._links,
                    postId: respObj.postId,
                    email: respObj.email,
                    content: respObj.content,
                    title: respObj.title,
                    createdAt: respObj.createdAt,
                    commentsLength: respObj.commentsLength,
                    isDeleted: respObj.isDeleted,
                    fileCnt: respObj.fileCnt,
                })
            })
            .catch((e) => {
                console.log("실패!!!!!!!!!" + e);

            });
    },
    getFiles({commit, dispatch}, payload) {
        this.$axios.get('http://localhost:8080/getFilesUrl/'+ payload.postId)
            .then((res) => {
                    const Obj = JSON.stringify(res.data);
                    const respObj = JSON.parse(Obj);
                commit('getFiles',
                    {
                        postId: payload.postId,
                        uri: respObj.fileDownloadUri,
                        fileName: respObj.fileName,
                        fileId: respObj.fileId,
                    });

                })
            .catch((err) => {
                console.log("getFiles err ", err);
            })
    } ,
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
                fileId: payload.fileId,
            }, config
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
                    this.$router.push({
                        path: '/',
                    });
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
    deleteFile({commit},payload) {
        this.$axios.delete(`http://localhost:8080/deleteFile/${payload.fileId}`)
            .then((res) => {
                    commit('deleteFile', payload)
                }
            )
            .catch((err) => {
                console.log(err)
            })
        ;
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

    },
    loadPosts: throttle(async function ({commit, state}, payload) {
        pagination += 1;
        if (payload && payload.reset)
            pagination = 0;

        await this.$axios.get('http://localhost:8080/posts/free?size=5&sort=postId,DESC&page=' + pagination)
            .then((res) => {
                console.log("posts/loadPosts 실행성공 pagination=", pagination);
                const Obj = JSON.stringify(res.data);
                const respObj = JSON.parse(Obj);
                const json = respObj._embedded.postList;
                if (payload && payload.reset) {
                    commit('loadPosts', {
                        data: json,
                        reset: true,
                    });
                    return;
                }
                if (state.hasMorePost) {
                    commit('loadPosts', {
                        data: json,
                    });
                    return;
                }

            })
            .catch((err) => {
                console.error("loadPosts err catch! =>>   ", err);
            });


    }, 2000),

    searchPosts: throttle(async function ({commit, state}, payload) {
        pagination += 1;
        if (payload && payload.reset)
            pagination = 0;

        await this.$axios.get('http://localhost:8080/posts/free?size=5&sort=postId,DESC&page=' + pagination + '&filter='+payload.filter)
            .then((res) => {
                console.log("posts/loadPosts 실행성공 pagination=", pagination);
                const Obj = JSON.stringify(res.data);
                const respObj = JSON.parse(Obj);
                try {
                    const json = respObj._embedded.postList;
                    console.log("json => ", json);
                    if (payload && payload.reset) {
                        commit('loadPosts', {
                            data: json,
                            reset: true,
                        });
                        return;
                    }
                    if (state.hasMorePost) {
                        commit('loadPosts', {
                            data: json,
                        });
                        return;
                    }
                }catch (e) {
                    console.log("search Posts error msg=", e)
                    // commit('loadPosts', {
                    //     data: respObj._embedded,
                    // });
                    // return;
                }

            })
            .catch((err) => {
                console.error("loadPosts err catch! =>>   ", err);
            });


    }, 2000),
    memberPosts: throttle(async function ({commit, state}, payload) {
            if (state.hasMorePost) {
                pagination += 1;
                if (payload && payload.reset)
                    pagination = 0;

                await this.$axios.get('http://localhost:8080/posts/profile/' + payload.email + '?size=5&sort=postId,DESC&page=' + pagination)
                    .then((res) => {
                        console.log("posts/loadPosts 실행성공 pagination=", pagination);
                        const Obj = JSON.stringify(res.data);
                        const respObj = JSON.parse(Obj);
                        const json = respObj._embedded.postList;
                        if (payload && payload.reset) {
                            commit('loadPosts', {
                                data: json,
                                reset: true,
                            });
                            return;
                        }
                        if (state.hasMorePost) {
                            commit('loadPosts', {
                                data: json,
                            });
                            return;
                        }

                    })
                    .catch((err) => {
                        console.error("loadPosts err catch! =>>   ", err);
                    });

            }
    }, 2000),
    loadComments({commit}, payload) {
        // console.log("posts/loadComments호출")
        this.$axios.get(`http://localhost:8080/comments/${payload.postId}`)
            .then((res) => {
                // console.log("comments 성공")
                const Obj = JSON.stringify(res.data);
                const respObj = JSON.parse(Obj);
                // console.log("=========")
                // console.log(respObj)
                commit('loadComments',
                    {
                        postId: payload.postId,
                        data: respObj,
                        CommentsLength: respObj.length,
                    });

            })
            .catch((err) => {
                console.log("comments 실패", err)
            })
    },
    uploadImages({commit}, payload) {
        let token = localStorage.getItem("authtoken");

        let xhr = new XMLHttpRequest();
        let xhr2 = new XMLHttpRequest();
        xhr.onreadystatechange = function () { // 요청에 대한 콜백
            if (xhr.readyState === xhr.DONE) { // 요청이 완료되면
                if (xhr.status === 200 || xhr.status === 201) {
                    const respObj = JSON.parse(xhr.response);
                    console.log("============================");
                    console.log(respObj)
                    xhr2.open('GET', 'http://localhost:8080' + respObj.fileDownloadUri);
                    xhr2.send()
                    console.log("uplaodImages commit 준비")
                    commit('uploadImages',
                        {
                            postId: payload.postId,
                            uri: respObj.fileDownloadUri,
                            fileName: respObj.fileName,
                            fileId: respObj.fileId,
                        });
                    console.log("uplaodImages commit 완료")

                } else {

                    console.error(xhr.responseText);
                    alert("파일 전송 용량 초과");
                }
            }
        };
        xhr.open('POST', 'http://localhost:8080/fileUpload'); // 메소드와 주소 설정
        xhr.send(payload.file); // 요청 전송


        // this.$axios.post('http://localhost:8080/fileUpload',
        //     {
        //         payload,
        //     }, {
        //         headers: {'Content-Type': 'multipart/form-data'},
        //
        //     }
        // )
        //     .then((res) => {
        //         const respObj = JSON.parse(res.data);
        //         commit('uploadImages',
        //             {
        //                 postId: 74,
        //                 uri: respObj.fileDownloadUri,
        //             });
        //         console.log("uplaodImages commit 완료")
        //         console.log("file upload success!")
        //         console.log(res.data)
        //
        //     })
        //     .catch((err) => {
        //         console.log("file upload failed")
        //         console.log(err)
        //     })


    },
};