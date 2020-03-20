<template>
    <v-container>
        <post-form v-if="me"/>
        <div>
            <post-card v-for="p in mainPosts" :key="p.id" :post="p" />
        </div>
    </v-container>
</template>

<script>
    import PostCard from "../components/PostCard";
    import PostForm from "../components/PostForm";

    export default {
        name: "index.vue",
        components:{
            PostForm,
            PostCard,
        },
        data(){
            return {
                name:'Nuxt.js',
            };
        },
        computed:{
            me() {
                return this.$store.state.user.me;
            },
            mainPosts() {
                return this.$store.state.posts.mainPosts;
            }
        }

    }
</script>

<style scoped>

</style>