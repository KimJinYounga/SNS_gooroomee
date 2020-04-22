<template>
    <v-container v-if="post">
        <post-detail :post="post"/>
        <div v-for="f in post.uploadImages" >
            <a v-bind:href="downloadUri(f.uri)">{{f.fileName}}</a>
        </div>
    </v-container>
    <div v-else>
        해당 아이디의 게시글이 존재하지 않습니다.
    </div>
</template>

<script>
    import PostCard from "~/components/PostCard";
    import PostDetail from "../../../components/PostDetail";
    export default {
        fetch() {

        },
        asyncData() {

        },
        components: {
            PostDetail,
            PostCard,
        },
        mounted() {
            this.$store.dispatch('posts/getFiles', {
                postId: this.$route.params.id
            });
        },
        computed: {
            post() {
                return this.$store.state.posts.mainPosts.find(v => v.postId === parseInt(this.$route.params.id, 10)); // 동적 라우팅(파일이름의 '_id')
            },
        },
        methods: {
            downloadUri(link) {
                return "http://localhost:8080" + link;
            },
        },
    }
</script>

<style>
</style>