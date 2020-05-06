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
        <v-btn color="green" dark absolute top right type="submit">전송</v-btn>

    </v-form>
</template>

<script>
    export default {
        name: "CommentForm",
        props:{
            parentsId: {
                // type: Number,
            },
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
                // parentId: this.parentsId,
            }
        },
        computed: {
            // parentId() {
            //   return this.parentsId;
            // },
            me() {
                return this.$store.state.user.me;
            },
        },
        methods:{
            onSubmitForm() {
                if(this.$refs.form.validate()) {
                    console.log("this.parentsId 1 -> ", this.parentsId);
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
                            this.parentsId = '';
                            console.log("this.parentsId 2 -> ", this.parentsId);
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