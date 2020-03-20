<template>
    <div>
        <v-container>
            <v-card style="margin-bottom: 20px">
                <v-container>
                    <v-subheader>내 프로필</v-subheader>
                    <v-form v-model="valid" @submit.prevent="onChangeEmail">
                        <v-text-field
                                v-model="email"
                                label="이메일"
                                :rules="emailRules"
                                required
                        />
                        <v-btn dark color="blue" type="submit">수정</v-btn>
                    </v-form>
                </v-container>
            </v-card>
            <v-card style="margin-bottom: 20px">
                <v-container>
                    <v-subheader>팔로잉</v-subheader>
                    <follow-list />
                </v-container>
            </v-card>
            <v-card style="margin-bottom: 20px">
                <v-container>
                    <v-subheader>팔로워</v-subheader>
                    <follow-list />
                </v-container>
            </v-card>

        </v-container>
    </div>
</template>

<script>
    export default {
        name: "profile.vue",
        data() {
            return {
                name: 'Nuxt.js',
                valid:false,
                email:'',
                emailRules: [
                    v => !!v || '이메일을 입력하세요.',
                ]
            }
        },
        methods:{
            onChangeEmail() {
                this.$store.dispatch('user/changeEmail', {
                    email:this.email,
                });
            }

        },
        middleware: 'authenticated',
    }
</script>

<style scoped>

</style>