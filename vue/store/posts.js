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
    removeMainPost(state, payload) {
        const index = state.mainPosts.findIndex(v => v.id === payload.id);
        state.mainPosts.splice(index, 1);
    },
    addComment(state, payload) {
        const index = state.mainPosts.findIndex(v => v.id === payload.Id);
        state.mainPosts[index].Comments.push(payload);
    },
    loadPosts(state, payload) {
        state.mainPosts = state.mainPosts.concat(payload);
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
        )
            .then((res) => {
                console.log("성공!!!!!!!!!"+res.data);
                commit('addMainPost', payload);
            })
            .catch((e) => {
                console.log("실패!!!!!!!!!"+e.getMessage);

            });
    },
    remove({commit}, payload) {
        commit('removeMainPost', payload);
    },
    addComment({commit}, payload) {
        commit('addComment', payload);
    },
    loadPosts({commit, state}, payload) {
        console.log("2. loadPosts 실행중");
        if (state.hasMorePost) {
            console.log("3. loadPosts2 실행중");
            this.$axios.get('http://localhost:8080/posts/free?size=5&sort=postId,DESC&page='+pagination)
                .then((res) => {
                    console.log("4. axios실행");
                    pagination+=1;
                    const Obj = JSON.stringify(res.data);
                    const respObj = JSON.parse(Obj);
                    console.log("=====================");
                    console.log(Obj);
                    console.log(respObj._embedded.postList);
                    commit('loadPosts', respObj._embedded.postList);
                })
                .catch((err) => {
                    console.error("loadPosts err catch! =>>   ", err);

                });

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