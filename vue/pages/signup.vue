<template>
    <div>
        <v-container>
            <v-card>
                <v-subheader>회원가입</v-subheader>
                <v-form ref="form" v-model="valid" @submit.prevent="onSubmitForm">
                    <v-container>
                        <v-text-field
                                v-model="email"
                                label="이메일"
                                type="email"
                                :rules="emailRules"
                                required
                        />
                        <v-text-field
                                v-model="name"
                                label="이름"
                                type="text"
                                :rules="nameRules"
                                required
                        />
                        <v-text-field
                                v-model="password"
                                label="비밀번호"
                                type="password"
                                :rules="passwordRules"
                                required
                        />
                        <v-text-field
                                v-model="passwordCheck"
                                label="비밀번호 확인"
                                type="password"
                                :rules="passwordCheckRules"
                                required
                        />
                        <v-checkbox
                                v-model="terms"
                                :rules="[v=>!!v || '약관에 동의해야 합니다.']"
                                required
                                label="정보 수집에 동의합니다."
                        />
                        <v-btn color="green" type="submit" :disabled="!valid">가입하기</v-btn>
                    </v-container>
                </v-form>
            </v-card>
        </v-container>
    </div>

</template>

<script>
    export default {
        name: "signup",
        data() {
            return {
                valid:false,
                email:'',
                password:'',
                passwordCheck:'',
                name:'',
                terms:false,
                emailRules: [
                    v=> !!v || '이메일은 필수입니다.',
                    v=> /.+@.+/.test(v) || '이메일이 유효하지 않습니다.',
                ],
                nameRules: [
                    v => !!v || '이름은 필수입니다.',
                ],
                passwordRules: [
                    v => !!v || '패스워드는 필수입니다.',
                ],
                passwordCheckRules: [
                    v => !!v || '패스워드 확인은 필수입니다.',
                    v =>  v === this.password || '비밀번호가 일치하지 않습니다.',
                ]
            }
        },
        computed:{
          me() {
              return this.$store.state.user.me;
          }
        },
        watch: {
            me(value) {
                if(value) {
                    this.$router.push({
                        path:'/',
                    });
                }
            }
        },
        methods: {
            onSubmitForm() {
                if(this.$refs.form.validate()) {
                    this.$store.dispatch('user/signUp', {
                        name:this.name,
                        email:this.email,
                        password:this.password,
                    })
                    .then(() => {
                    })
                        .catch((err) => {
                            alert("아이디가 중복.");
                            console.error("다른 아이디를 사용해주세요", err);
                        });

                }

            }
        },
        head() {
            return {
                title:'회원가입'
            }
        },
        middleware: 'anonymous',
    }
</script>

<style scoped>

</style>