export const state = () => ({
    name: 'posts',
});

export const mutations = {
    BYE(state) {
        state.name = 'good bye posts';
    }
};