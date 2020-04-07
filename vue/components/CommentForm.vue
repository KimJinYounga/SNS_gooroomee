<template>
    <v-form ref="form" v-model="valid" style="position: relative" @submit.prevent="onSubmitForm">
        <v-textarea
         v-model="Comments"
         filled
         auto-grow
         row-height="8"
         label="댓글 달기"
         :hide-details="hideDetails"
         :success="success"
         :success-messages="successMessages"
         @input="onChangeTextarea"
        />
        <v-btn color="green" dark absolute top right type="submit">삐약</v-btn>

    </v-form>
</template>

<script>
    export default {
        name: "CommentForm",
        props:{
            postId:{
                type: Number,
                required:true,
            },
        },
        data() {
            return {
                valid:false,
                Comments:'',
                success:false,
                successMessages:'',
                hideDetails:true,
            }
        },
        computed: {
            me() {
                return this.$store.state.user.me;
            },
        },
        methods:{
            onSubmitForm() {
                if(this.$refs.form.validate()) {
                    this.$store.dispatch('posts/addComment', {
                        // id:Date.now(),
                        parentsId: this.parentsId,
                        postId:this.postId,
                        // parentsId도 추가해야함 (계층형 댓글 구조)
                        Comments:this.Comments,
                        User: {
                            nickname: this.me,
                        }
                    })
                        .then(() => {
                            this.Comments='';
                            this.success=true;
                            this.successMessages='댓글이 작성되었습니다.';
                            this.hideDetails=false;
                        })
                        .catch(() => {

                        });
                }
            },
            onChangeTextarea(value) {
                if(value.length) {
                    this.hideDetails=true;
                    this.success = false;
                    this.successMessages='';
                }

            }
        }
    }
</script>

<style scoped>

</style>