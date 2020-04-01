export const state = () => ({
    mainPosts: [],
    hasMorePost: true,
    Comments: [],
});


const limit = 5;
let pagination = 0;
export const mutations = {
    addMainPost(state, payload) {
        state.mainPosts.unshift(payload); //최신 게시글을 맨 앞에 보여줄 것이므로 push가 아닌 unshift
    },
    removeMainPost(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === payload.postId);
        state.mainPosts.splice(index, 1);
    },
    addComment(state, payload) {
        const index = state.mainPosts.findIndex(v => v.postId === `${payload.postId}`);
        state.mainPosts[index].Comments.push(payload);
    },
    loadPosts(state, payload) {
        state.mainPosts = state.mainPosts.concat(payload);
        state.hasMorePost = payload.length === limit;
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

                })
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
    },
    addComment({commit}, payload) {
        commit('addComment', payload);
    },
    loadPosts({commit, state}, payload) {
        if (state.hasMorePost) {
            return this.$axios.get('http://localhost:8080/posts/free?size=5&sort=postId,DESC&page=' + pagination)
                .then((res) => {
                    pagination += 1;
                    const Obj = JSON.stringify(res.data);
                    const respObj = JSON.parse(Obj);
                    commit('loadPosts', respObj._embedded.postList);
                })
                .catch((err) => {
                    console.error("loadPosts err catch! =>>   ", err);

                });

        }
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