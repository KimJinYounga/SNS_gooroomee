export const state = () => ({
    mainPosts: [],
    hasMorePost:true,
});

const totalPosts = 40;
const limit = 10;

export const mutations = {
    addMainPost(state, payload) {
        state.mainPosts.unshift(payload); //최신 게시글을 맨 앞에 보여줄 것이므로 push가 아닌 unshift
    },
    removeMainPost(state,payload) {
        const index = state.mainPosts.findIndex(v => v.id === payload.id);
        state.mainPosts.splice(index,1);
    },
    addComment(state, payload) {
        const index = state.mainPosts.findIndex(v => v.id === payload.postId);
        state.mainPosts[index].Comments.push(payload);
    },
    loadPosts(state) {
        const diff = totalPosts - state.mainPosts.length;
        const fakePosts = Array(diff > limit ? limit:diff ).fill().map(v => ({
            id: Math.random().toString(),
            User : {
                id: 1,
                nickname:'asd@asd',
            },
            content: `Hello infinite scrolling~ ${Math.random()}`,
            Comments:[],
            Images:[],
        }));
        state.mainPosts = state.mainPosts.concat(fakePosts); // 가짜 데이터 10개 추가
        state.hasMorePost = fakePosts.length === limit;
    },
};

export const actions = {
    add({ commit }, payload) {
        // 서버에 게시글 등록 요청 보냄
        commit('addMainPost', payload);
    },
    remove({ commit }, payload) {
        commit('removeMainPost', payload);
    },
    addComment({ commit }, payload) {
        commit('addComment', payload);
    },
    loadPosts({ commit, state }, payload) {
        if(state.hasMorePost) {
            commit('loadPosts');
        }
    },
};