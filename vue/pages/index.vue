<template>
    <v-container>
        <div>
            <post-card></post-card>
            <post-card></post-card>
        </div>
    </v-container>
</template>

<script>
    import PostCard from "../components/PostCard";

    export default {
        name: "index.vue",
        components:{
            PostCard,
        }

    }
</script>

<style scoped>

</style>