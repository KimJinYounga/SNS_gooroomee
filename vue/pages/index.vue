<template>
    <v-container>

        <post-form v-if="authtoken"/>
        <div>
            <post-card v-for="p in mainPosts" :key="p.postId" :post="p"/>
        </div>
    </v-container>
</template>

<script>
    import PostCard from "../components/PostCard";
    import PostForm from "../components/PostForm";

    export default {
        name: "index.vue",
        components: {
            PostForm,
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
        // fetch({store}) { // 처음 시작할 때 데이터를 넣어줌
        //     console.log("fetch ")
        //     return store.dispatch('posts/loadPosts'); // 화면이 뜨기 전에 미리 게시글 10개를 서버에서 로딩함
        // },

        mounted() {
            console.log("mounted");
            this.$store.dispatch('posts/loadPosts');
            window.addEventListener('scroll', this.onScroll);
            this.$store.dispatch('user/getMemberInfo')
        },
        beforeDestroy() {
            window.removeEventListener('scroll', this.onScroll);
        },
        methods: {
            onScroll() {
                console.log('scroll');
                if (window.scrollY + document.documentElement.clientHeight > document.documentElement.scrollHeight - 100) {
                    if (this.hasMorePost) {
                        console.log("index의 onScroll() loadPosts 호출");
                        this.$store.dispatch('posts/loadPosts');
                    }
                }
            },
        },
    };
</script>

<style scoped>

</style>