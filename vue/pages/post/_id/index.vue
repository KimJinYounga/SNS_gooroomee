<template>
    <v-container v-if="post">
        <post-detail :post="post"/>
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
        computed: {
            post() {
                return this.$store.state.posts.mainPosts.find(v => v.postId === parseInt(this.$route.params.id, 10)); // 동적 라우팅(파일이름의 '_id')
            }
        }
    }
</script>

<style>

</style>
