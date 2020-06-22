export const state = () => ({

});

export const mutations = () => ({

});
// layout에 fetch를 넣는 대신 nuxtServerInit을 사용하면 모든 페이지에서 화면그려지기 전에 실행함.
export const actions = {
    nuxtServerInit({commit, dispatch, state}, {req}) {
        // return dispatch('user/getMemberInfo');
    }
};