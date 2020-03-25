<template>
    <v-card style="margin-bottom: 20px">
        <v-container>
            <v-form ref="form" v-model="valid" @submit.prevent="onSubmitForm">
                <v-text-field
                        v-model="title"
                        label="제목"
                        type="text"
                />
                <v-textarea
                        v-model="content"
                        outlined
                        auto-grow
                        clearable
                        label="어떤 신기한 일이 있었나요?"
                        :hide-details="hideDetails"
                        :success-messages="successMessages"
                        :success="success"
                        :rules="[v => !!v.trim() || '내용을 입력하세요.']"
                        @input="onChangeTextarea"
                />
                <v-btn type="submit" color="green" absolute right>짹짹</v-btn>
                <input ref="imageInput" type="file" multiple hidden @change="onChangeImages">
                <v-btn @click="onClickImageUpload" type="button">이미지 업로드</v-btn>
            </v-form>

        </v-container>
    </v-card>
</template>

<script>
    import {mapState} from 'vuex';

    export default {
        name: "PostForm",
        data() {
            return {
                valid: false,
                hideDetails: false,
                successMessages: '',
                success: false,
                content: '',
                title:'',
            }
        },
        computed: {
            ...mapState('user', ['me'])
        },
        methods: {
            onChangeTextarea(value) {
                if (value.length) {
                    this.hideDetails = true;
                    this.success = false;
                    this.successMessages = '';
                }
            },
            onSubmitForm() {
                if (this.$refs.form.validate()) {
                    this.$store.dispatch('posts/add', {
                        content: this.content,
                        email:this.me,
                        title:this.title,
                        User: {
                            email: this.me,
                        },
                        Comments: [],
                    })
                        .then(() => {
                            this.content = '';
                            this.title = '';
                            this.hideDetails = false;
                            this.success = true;
                            this.successMessages = '게시글 등록 성공!';
                        })
                        .catch(() => {

                        })
                    ;
                }

            },
            onClickImageUpload() {
                this.$refs.imageInput.click();
            },
            onChangeImages(e) {
                console.log(e.target.files);
                const imageFormData = new FormData();
                [].forEach.call(e.target.files, (f) => {
                    imageFormData.append('file', f);
                });
                this.$store.dispatch('posts/uploadImages', imageFormData);
            },
        },
    };
</script>

<style scoped>

</style>