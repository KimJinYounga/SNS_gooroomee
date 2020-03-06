<template>
    <nav>
        <div>profile page</div>
    </nav>
    
</template>

<script>
    export default {
        name: "profile.vue",
        data() {
            return {
                name:'Nuxt.js',
            }
        },
        head() {
            return {
                title:'프로필'
            }
        }
    }
</script>

<style scoped>

</style>