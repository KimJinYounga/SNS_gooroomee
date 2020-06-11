/*
vuex module
 */


import throttle from "lodash.throttle";
import Vue from "../nuxt.config";

export const state = () => ({
    me: null,
    authtoken: null,
    followerList: [],
    followingList: [],
    profile: null,
    memberName: null,
    Mem: null,
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
    },
    changeEmail(state, payload) {
        state.me.email = payload.email;
    },
    getProfile(state, payload) {
        // Vue.set(state.profile, 'name', payload.name );
        state.profile=payload;
    },
    changeProfileImage(state, payload) {
        state.profile = payload;
    },
    getMemberName(state, payload) {
      state.memberName = payload;
    },
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
            // console.log(data);
            let token = data.headers.authtoken;
            localStorage.setItem("authtoken", token);
            // console.log("===========", token);
            // dispatch('getMemberInfo');

            commit('setMe', payload.email);
            commit('setAuth', token);

        }).catch((err) => {
            alert("다시 로그인 해주세요!");
            console.error("다시 로그인하라!!!!", err);
        });
    },
    getMemberInfo({dispatch, commit}) {
        if (localStorage.getItem("authtoken")) {
            let token = localStorage.getItem("authtoken");
            let config = {
                headers: {
                    "authtoken": token,
                },
            };
            this.$axios.get('http://localhost:8080/auth/member', config)
                .then(res => {
                    commit('setMe', res.data.email);
                    // console.log("!!!!!!getmemberinfo_auth" + JSON.stringify(res.data));
                    commit('setAuth', res.data)

                })
                .catch(err => {
                    console.log("get axios user" + err)
                });
        }

    },
    getProfile({dispatch, commit}, payload) {
        if (localStorage.getItem("authtoken")) {
            let token = localStorage.getItem("authtoken");
            let config = {
                headers: {
                    "authtoken": token,
                },
            };
            this.$axios.get('http://localhost:8080/auth/member/'+payload, config)
                .then(res => {
                    console.log("getProfile => ", res.data);
                    // commit('getProfile',res.data.name);
                    commit('getProfile',res.data.name);
                    // commit('getProfile',res.data.name);
                    // commit('setMe', res.data.email);
                    // commit('setAuth', res.data)
                })
                .catch(err => {
                    console.log("get axios user" + err)
                });
        }

    },
    getMemberName({dispatch, commit}, payload) {
        if (localStorage.getItem("authtoken")) {
            let token = localStorage.getItem("authtoken");
            let config = {
                headers: {
                    "authtoken": token,
                },
            };
            this.$axios.get('http://localhost:8080/auth/member/'+payload, config)
                .then(res => {
                    console.log("getProfile => ", res.data);
                    // commit('getProfile',res.data.name);
                    commit('getMemberName',res.data.name);
                    // commit('getProfile',res.data.name);
                    // commit('setMe', res.data.email);
                    // commit('setAuth', res.data)
                    // getMemberName
                })
                .catch(err => {
                    console.log("get axios user" + err)
                });
        }

    },
    changeProfileImage({commit}, payload) {
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
                    commit('changeProfileImage', respObj.fileDownloadUri);
                        // {
                        //     postId: payload.email,
                        //     uri: respObj.fileDownloadUri,
                        //     fileName: respObj.fileName,
                        //     fileId: respObj.fileId,
                        // });
                    console.log("changeProfileImage commit 완료")

                } else {
                    console.error(xhr.responseText);
                }
            }
        };
        xhr.open('POST', `http://localhost:8080/fileUpload/${payload.email}`); // 메소드와 주소 설정
        xhr.send(payload.file); // 요청 전송
    },
    getProfileImage({commit}, payload) {
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
                    commit('getProfile', respObj.fileDownloadUri);
                    console.log("changeProfileImage commit 완료")

                } else {
                    console.error(xhr.responseText);
                }
            }
        };
        xhr.open('GET', `http://localhost:8080/getProfileUrl/${payload.email}`);
        xhr.send(); // 요청 전송

        // dfsdfsf##################
        // this.$axios.get('http://localhost:8080/getProfileUrl/'+payload.email)
        //     .then(res => {
        //         const respObj = JSON.parse(res.data);
        //         commit('getProfile',respObj.fileDownloadUri);
        //         // commit('getProfile',res.data.name);
        //         // commit('setMe', res.data.email);
        //         // commit('setAuth', res.data)
        //
        //     })
        //     .catch(err => {
        //         console.log("get axios user" + err)
        //     });
    },
    logOut({commit}, payload) {
        commit('setAuth', null);
        commit('getProfile', null);
        localStorage.removeItem('authtoken');
    },
    changeName({commit}, payload) {
        commit('changeEmail', payload);
    },
};