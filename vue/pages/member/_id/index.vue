<template>
    <v-container>
        <div>
            <post-card v-for="p in mainPosts" :key="p.postId" :post="p"/>
        </div>
    </v-container>
</template>

<script>
    import PostCard from "../../../components/PostCard";
    export default {
        layout: 'profile',
        name: "index.vue",
        components: {
            PostCard,
        },
        data() {
            return {
                name: 'Nuxt.js',
            };
        },

        computed: {
            authtoken() {
                return this.$store.state.user.authtoken;
            },
            me() {
                return this.$store.state.user.me;
            },
            mainPosts() {
                return this.$store.state.posts.mainPosts;
            },
            hasMorePost() {
                return this.$store.state.posts.hasMorePost;
            }
        },
        fetch({store, params}) { // 처음 시작할 때 데이터를 넣어줌
            console.log("fetch ")
            return store.dispatch('posts/memberPosts', {
                reset: true,
                email: params.id,
            });
        },

        mounted() {
            window.addEventListener('scroll', this.onScroll);
            this.$store.dispatch('user/getMemberInfo')
        },
        beforeDestroy() {
            window.removeEventListener('scroll', this.onScroll);
        },
        methods: {
            onScroll() {
                console.log('scroll');
                if (window.scrollY + document.documentElement.clientHeight > document.documentElement.scrollHeight - 200) {
                    if (this.hasMorePost) {
                        console.log("search index의 onScroll() searchPosts 호출");
                        this.$store.dispatch('posts/memberPosts', {
                            email: this.$route.params.id,
                        });
                    }
                }
            },
        },
    };
</script>

<style scoped>

</style>