<template>
    <div>
        <v-container>
            <template>
                <v-container>
                    <v-card
                            max-width="375"
                    >
                        <v-img
                                :src="profilePath"
                                height="300px"
                                dark
                        >
                            <v-row style="">
                                <v-card-title>

                                    <v-spacer></v-spacer>
                                    <input ref="imageInput" type="file" multiple hidden @change="onChangeImages">
                                    <v-btn dark icon class="mr-4" @click="changeProfile" type="button">
                                        <v-icon>mdi-camera</v-icon>
                                    </v-btn>

                                    <v-btn dark icon class="mr-4">
                                        <v-icon>mdi-pencil</v-icon>
                                    </v-btn>

                                    <v-btn dark icon>
                                        <v-icon>mdi-dots-vertical</v-icon>
                                    </v-btn>
                                </v-card-title>

                                <v-spacer></v-spacer>
                            </v-row>
                        </v-img>

                        <v-list two-line>

                            <v-list-item>
                                <v-list-item-icon>
                                    <v-icon color="indigo">mdi-email</v-icon>
                                </v-list-item-icon>

                                <v-list-item-content>
                                    <v-list-item-title>{{Auth.email}}</v-list-item-title>
                                    <v-list-item-subtitle>Account</v-list-item-subtitle>
                                </v-list-item-content>
                            </v-list-item>

                            <v-list-item>
                                <v-list-item-action></v-list-item-action>

                                <v-list-item-content>
                                    <v-list-item-title>{{Auth.name}}</v-list-item-title>
                                    <v-list-item-subtitle>Name</v-list-item-subtitle>
                                </v-list-item-content>
                            </v-list-item>

                            <v-list-item @click="">
                                <v-list-item-content>
                                    <v-list-item-title>저장</v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-list>

                    </v-card>
                </v-container>
            </template>
<!--            <v-card style="margin-bottom: 20px">-->
<!--                <v-container>-->
<!--                    <v-subheader>내 프로필</v-subheader>-->
<!--                    <v-text-field v-model="email" readonly></v-text-field>-->
<!--                    <v-form v-model="valid" @submit.prevent="onChangeEmail">-->
<!--                        <v-text-field-->
<!--                                v-model="name"-->
<!--                                label="이름"-->
<!--                                required-->
<!--                        />-->

<!--                        <v-btn dark color="blue" type="submit">수정</v-btn>-->
<!--                    </v-form>-->
<!--                </v-container>-->
<!--            </v-card>-->
<!--            <v-card style="margin-bottom: 20px">-->
<!--                <v-container>-->
<!--                    <v-subheader>비밀번호 변경</v-subheader>-->
<!--                </v-container>-->
<!--            </v-card>-->
<!--            <v-card style="margin-bottom: 20px">-->
<!--                <v-container>-->
<!--                    <v-subheader>팔로잉</v-subheader>-->
<!--                </v-container>-->
<!--            </v-card>-->
<!--            <v-card style="margin-bottom: 20px">-->
<!--                <v-container>-->
<!--                    <v-subheader>팔로워</v-subheader>-->
<!--                </v-container>-->
<!--            </v-card>-->

        </v-container>
    </div>
</template>

<script>
    import Profile from "../components/Profile";
    export default {
        components: {
            Profile,
        },
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
            Auth() {
                return this.$store.state.user.authtoken;
            },
            profilePath() {
                if (this.$store.state.user.profile === null) {
                    return "https://hubbee-s3.s3.amazonaws.com/static/images/default/default_profile.jpg";
                }
                return "http://localhost:8080"+this.$store.state.user.profile;
            }
        },
        mounted() {

            this.$store.dispatch('user/getProfileImage', {
                email: this.email,
            });
        },
        methods:{
            changeProfile() {
                this.$refs.imageInput.click();
            },
            onChangeImages(e) {
                console.log("e.target => ", e.target)
                console.log("e.target.files => ",e.target.files);
                const imageFormData = new FormData();
                [].forEach.call(e.target.files, (f) => {
                    imageFormData.append('file', f);
                    console.log("f => ", f);
                });

                console.log("boundary => ", imageFormData._boundary);

                this.$store.dispatch('user/changeProfileImage', {
                    file: imageFormData,
                    email: this.email,
                });
            },
            onChangeEmail() {
                this.$store.dispatch('user/changeName', {
                    name:this.email,
                });
            },

        },
        middleware: 'authenticated',
    }
</script>

<style scoped>

</style>