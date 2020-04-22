


<template>
    <v-container>
    <v-card
            max-width="375"
            class="mx-auto"
    >
        <v-img
                src="http://localhost:8080/fileDownload/1a9974e0-5507-47ee-9a52-6e6444f3a87f"
                height="300px"
                dark
        >
            <v-row style="">
                <v-card-title>

                    <v-spacer></v-spacer>

                    <v-btn dark icon class="mr-4">
                        <v-icon>mdi-pencil</v-icon>
                    </v-btn>

                    <v-btn dark icon>
                        <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                </v-card-title>

                <v-spacer></v-spacer>

<!--                <v-card-title class="white&#45;&#45;text pl-12 pt-12">-->
<!--                    <div class="display-1 pl-12 pt-12">{{Profile}}</div>-->
<!--                </v-card-title>-->
            </v-row>
        </v-img>

        <v-list two-line>
            <v-list-item @click="">
                <v-list-item-content>
                    <v-list-item-title>메세지 보내기</v-list-item-title>
                </v-list-item-content>
            </v-list-item>

            <v-divider inset></v-divider>

            <v-list-item>
                <v-list-item-icon>
                    <v-icon color="indigo">mdi-email</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                    <v-list-item-title>{{email}}</v-list-item-title>
                    <v-list-item-subtitle>Account</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>

            <v-list-item>
                <v-list-item-action></v-list-item-action>

                <v-list-item-content>
                    <v-list-item-title>{{Profile}}</v-list-item-title>
                    <v-list-item-subtitle>Name</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>

            <v-divider inset></v-divider>

            <v-list-item @click="">
                <v-list-item-icon>
                    <v-icon color="indigo">mdi-map-marker</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                    <v-list-item-title>-</v-list-item-title>
                    <v-list-item-subtitle>-</v-list-item-subtitle>
                </v-list-item-content>
            </v-list-item>
        </v-list>

    </v-card>
    </v-container>
</template>


<script>
    export default {
        props: {

        },
        data() {
            return {
                email: this.$route.params.id,
                // name: this.$store.dispatch('user/getProfile', this.email),
            };
        },
        computed: {
            Profile() {
                return this.$store.state.user.profile;
            }
        },
        mounted() {
            this.$store.dispatch('user/getProfile', this.email);
        },
        methods: {
        }
    }
</script>

<style scoped>
    a {
        color: inherit;
        text-decoration: none;
    }
</style>