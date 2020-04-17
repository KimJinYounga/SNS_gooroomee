<template>
    <div>
        <v-container>
            <v-card style="margin-bottom: 20px">
                <v-container>
                    <v-subheader>내 프로필</v-subheader>
                    <v-text-field v-model="email" readonly></v-text-field>
                    <v-form v-model="valid" @submit.prevent="onChangeEmail">
                        <v-text-field
                                v-model="name"
                                label="이름"
                                required
                        />

                        <v-btn dark color="blue" type="submit">수정</v-btn>
                    </v-form>
                </v-container>
            </v-card>
<!--            <v-card style="margin-bottom: 20px">-->
<!--                <v-container>-->
<!--                    <v-subheader>비밀번호 변경</v-subheader>-->
<!--                </v-container>-->
<!--            </v-card>-->
            <v-card style="margin-bottom: 20px">
                <v-container>
                    <v-subheader>팔로잉</v-subheader>
                </v-container>
            </v-card>
            <v-card style="margin-bottom: 20px">
                <v-container>
                    <v-subheader>팔로워</v-subheader>
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
                name: '',
                valid:false,
                email: this.$store.state.user.me,
                password:'',
                passwordCheck:'',
            }
        },
        computed: {
        },
        methods:{
            onChangeEmail() {
                this.$store.dispatch('user/changeName', {
                    name:this.name,
                });
            }

        },
        middleware: 'authenticated',
    }
</script>

<style scoped>

</style>