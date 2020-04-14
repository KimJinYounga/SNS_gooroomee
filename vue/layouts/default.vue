<template>
    <v-app>
        <nav>
            <v-toolbar dark color="green">
                <v-toolbar-title>
                    <nuxt-link to="/">Gooroomee</nuxt-link>
                </v-toolbar-title>
                <v-spacer/>
                <v-toolbar-items>
                    <v-form @submit.prevent="onSearch">
                    <v-text-field label="검색"
                                  v-model="filter" hide-details prepend-icon="mdi-magnify"
                                  :style="{ display: 'flex', alignItems: 'center' }"/>
                    </v-form>
                    <v-btn text nuxt to="/profile" :style="{ display: 'flex', alignItems: 'center' }">
                        <div>프로필</div>
                    </v-btn>
                    <v-btn text nuxt to="/signup" :style="{ display: 'flex', alignItems: 'center' }">
                        <div>회원가입</div>
                    </v-btn>
                </v-toolbar-items>
            </v-toolbar>
        </nav>

        <v-row no-gutters>
            <v-col cols="12" xs="12" md="4">
                <login-form/>
            </v-col>
            <v-col cols="12" xs="12" md="8">
                <nuxt/>
            </v-col>
        </v-row>
    </v-app>
</template>

<script>
    import LoginForm from '~/components/LoginForm';

    export default {
        data() {
          return {
              filter: '',
          }
        },
        components: {
            LoginForm,
        },
        mounted() {
        },

        computed: {},
        methods: {
            onSearch() {
                console.log("onSearch .. ")
                return this.$store.dispatch('posts/loadPosts', {
                    reset: true,
                    filter:this.filter,
                });
            }
        }
    };
</script>

<style scoped>
    a {
        display: inline-block;
        text-decoration: none;
        color: inherit;
    }
</style>