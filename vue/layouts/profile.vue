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
                                  :style="{ display: 'flex', alignItems: 'center'}"/>
                    </v-form>
                    <v-menu bottom left>
                        <template v-slot:activator="{ on }">
                            <v-btn
                                    dark
                                    icon
                                    v-on="on"
                            >
                                <v-icon>mdi-dots-vertical</v-icon>
                            </v-btn>
                        </template>

                        <v-list>
                            <v-list-item
                                    v-for="(item, i) in [{title:'Email'}, {title: 'Content'}]"
                                    :key="i"
                                    @click=""
                            >
                                <v-list-item-title>{{ item.title }}</v-list-item-title>
                            </v-list-item>
                        </v-list>
                    </v-menu>
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
                <Profile />
            </v-col>
            <v-col cols="12" xs="12" md="8">
                <nuxt/>
            </v-col>
        </v-row>
    </v-app>
</template>

<script>
    import LoginForm from '~/components/LoginForm';
    import PostForm from "../components/PostForm";
    import Profile from "../components/Profile";
    export default {
        data() {
          return {
              filter: '',
          }
        },
        components: {
            PostForm,
            LoginForm,
            Profile,
        },
        mounted() {
        },

        computed: {},
        methods: {
            onSearch() {
                this.$router.push({
                    path: `/search/${this.filter}`,
                });
                this.filter='';
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