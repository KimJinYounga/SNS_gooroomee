/*
vuex module
 */
export const state = () => ({
    me: null,
    authtoken: null,
    followerList: [],
    followingList: [],
});
// state를 mutations를 통해 바꾼다.(비동기 작업은 mutation에서 할 수 없음,, 단순한 동기적 작업만 가능)
// mutations는 commit 으로 실행
export const mutations = {
    setMe(state, payload) {
        state.me = payload;
    },
    setAuth(state, payload) {
        state.authtoken = payload;
        console.log("state.authtoken == ", state.authtoken);
        // localStorage.authtoken = authtoken;
    },
    changeEmail(state, payload) {
        state.me.email = payload.email;
    }
};
// 비동기 작업을 위한 actions(복잡한 작업할때 사용)
export const actions = {

    signUp({commit, state}, payload) {
        // 서버에 회원가입 요청을 보내는 부분
        let {data} = this.$axios.post('http://localhost:8080/auth/signup', {
            email: payload.email,
            name: payload.name,
            password: payload.password,
        }).then((data) => {
            // commit('setMe', payload);
            this.$router.push({
                path: '/',
            });
        }).catch((err) => {
            alert("아이디중복입니다.");
            console.error("아이디 중복이다!!!!", err);
        });

    },
    logIn({dispatch, commit}, payload) {
        this.$axios.post('http://localhost:8080/auth/signin', {
            email: payload.email,
            password: payload.password,
        }).then((data) => {
            console.log(data);
            let token = data.headers.authtoken;
            localStorage.setItem("authtoken", token);
            dispatch('getMemberInfo');
            let config = {
                headers: {
                    "authtoken": data.headers.authtoken,
                },
            };
            // commit('setMe', payload);
            commit('setAuth', token);
        }).catch((err) => {
            alert("다시 로그인 해주세요!");
            console.error("다시 로그인하라!!!!", err);
        });
    },
    getMemberInfo({commit}) {
        let token = localStorage.getItem("authtoken");
        let config = {
            headers: {
                "authtoken": token,
            },
        };
        this.$axios.get('http://localhost:8080/auth/member', config)
            .then(res => {
                commit('setMe', res.data.email);
                console.log("!!!!!!getmemberinfo_auth" + JSON.stringify(res.data));
                commit('setAuth', res.data)
            })
            .catch(err => {
                console.log("get axios user" + err)
            });

    },
    logOut({commit}, payload) {
        commit('setAuth', null);
        localStorage.removeItem('authtoken');
    },
    changeEmail({commit}, payload) {
        commit('changeEmail', payload);
    },
};