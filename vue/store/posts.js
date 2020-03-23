export const state = () => ({
    mainPosts: [],
    hasMorePost: true,
});

const totalPosts = 40;
const limit = 10;

export const mutations = {
    addMainPost(state, payload) {
        state.mainPosts.unshift(payload); //최신 게시글을 맨 앞에 보여줄 것이므로 push가 아닌 unshift
    },
    removeMainPost(state, payload) {
        const index = state.mainPosts.findIndex(v => v.id === payload.id);
        state.mainPosts.splice(index, 1);
    },
    addComment(state, payload) {
        const index = state.mainPosts.findIndex(v => v.id === payload.postId);
        state.mainPosts[index].Comments.push(payload);
    },
    loadPosts(state, payload) {

        state.mainPosts = state.mainPosts.concat(payload); // 가짜 데이터 10개 추가
        state.hasMorePost = payload.length === limit;
    },

};

export const actions = {
    add({commit}, payload) {
        // 서버에 게시글 등록 요청 보냄
        this.$axios.post('http://localhost:8080/post/free', {
            content: payload.content,
            email: payload.email,
            title: payload.title,
        },
        //     {
        //     withCredentials: true,
        // }
        )
            .then((res) => {
                console.log("성공!!!!!!!!!"+res.data);
                commit('addMainPost', res.data);
            })
            .catch((e) => {
                console.log("실패!!!!!!!!!"+e.getMessage);

            });
        // commit('addMainPost', payload);
    },
    remove({commit}, payload) {
        commit('removeMainPost', payload);
    },
    addComment({commit}, payload) {
        commit('addComment', payload);
    },
    loadPosts({commit, state}, payload) {
        if (state.hasMorePost) {
            this.$axios.get('http://localhost:8080/posts/free')
                .then((res) => {
                    console.log(res.data);
                    commit('loadPosts', res.data);
                })
                .catch(() => {

                });
            // commit('loadPosts');
        }
    },
    uploadImages({commit}, payload) {
        this.$axios.post('http://localhost:8080/testUpload/1', {
            fileName:payload.fileName,
            fileType:payload.fileType,
            fileUrl:payload.fileUrl,
        }, {
            withCredentials: true,
        })
            .then((res) => {


            })
            .catch(() => {

            })

    },
};