export const state = () => ({
    hello: 'vuex',
});

export const mutations = () => ({
    bye(state) {
        state.hello = 'good bye';
    }
});