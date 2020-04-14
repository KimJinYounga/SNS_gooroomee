<template>
    <div style="margin-bottom: 20px">
        <v-card>
            <!--            <v-image/>-->
            <v-card-text>
                <nuxt-link :to="`/member/`+post.email">
                    <v-list-item>
                        <v-list-item-avatar color="teal">
                            <span>{{ post.email[0] }}</span>
                        </v-list-item-avatar>
                        <v-list-item-content>
                            <v-list-item-title>{{ post.email }}</v-list-item-title>
                            <v-list-item-subtitle>{{ post.createdAt}}</v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
                </nuxt-link>
                <nuxt-link :to="'/post/' + post.postId">
                    <v-list-item v-if="!post.isDeleted">
                        <p> {{ post.title }}</p>
                    </v-list-item>
                </nuxt-link>
                <v-list-item v-if="post.isDeleted">
                    삭제된 게시글 입니다.
                </v-list-item>

            </v-card-text>
            <div v-if="!post.isDeleted">
                <v-card-actions>
                    <v-btn text color="orange" @click="fav = !fav">
                        <v-icon>{{heartIcon}}</v-icon>
                    </v-btn>
                    <v-btn text color="orange" @click="onToggleComment">
                        <v-icon>mdi-comment-outline</v-icon>
                        {{ post.commentsLength}}
                    </v-btn>
                    <v-menu offset-y open-on-hover v-if="post.email === me">
                        <template v-slot:activator="{ on }">
                            <v-btn text color="orange" v-on="on">
                                <v-icon>mdi-dots-horizontal</v-icon>
                            </v-btn>
                        </template>
                        <div style="background: white">
                            <v-btn dark color="red" @click="openConfirm">삭제</v-btn>
                            <nuxt-link :to="'/post/edit/' + post.postId">
                                <v-btn text color="orange">수정</v-btn>
                            </nuxt-link>
                        </div>
                    </v-menu>
                </v-card-actions>
            </div>

        </v-card>
        <template v-if="commentOpened">
            <comment-form :post-id="post.postId" v-if="authtoken"></comment-form>
            <v-list>
                <!--                <div style = "background-color: rgba(0, 0, 0, 0.03)">-->

                <v-list-item v-for="c in post.Comments" :key="c.id" style="margin: 10px 0">
                    <v-list-item-avatar color="teal">
                        <span>{{c.email[0]}}</span>
                    </v-list-item-avatar>
                    <v-list-item-content class="comment">
                        <h3>{{c.email}}</h3>
                        <h6>{{c.createdAt}} </h6>
                        <div v-if="!c.isDeleted">{{c.comments}}</div>
                        <div v-else>삭제된 댓글 입니다.</div>
                    </v-list-item-content>
                    <v-menu offset-y open-on-hover v-if="c.email === me && !c.isDeleted">
                        <template v-slot:activator="{ on }">
                            <v-btn text color="teal" v-on="on">
                                <v-icon>mdi-dots-horizontal</v-icon>
                            </v-btn>
                        </template>
                        <div style="background: white">
                            <v-btn dark color="red" @click="openCommentConfirm(c.commentsId)">삭제</v-btn>
                            <!--                            <v-btn text color="teal" @click="onCommentEditPost">수정</v-btn>-->
                        </div>
                    </v-menu>
                </v-list-item>
                <!--                </div>-->

            </v-list>
        </template>

        <template>
            <div class="text-center">
                <v-dialog
                        v-model="dialog"
                        width="500"
                >

                    <v-card>
                        <v-card-title
                                class="headline grey lighten-2"
                                primary-title
                        >
                            정말 삭제하시겠습니까?
                        </v-card-title>

                        <v-divider></v-divider>

                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="primary"
                                   text @click="ok">Yes
                            </v-btn>
                            <v-btn color="primary"
                                   text @click="no">No
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </div>
        </template>

        <template>
            <div class="text-center">
                <v-dialog
                        v-model="commentdialog"
                        v-bind:value="commentsId"
                        width="500"
                >

                    <v-card>
                        <v-card-title
                                class="headline grey lighten-2"
                                primary-title
                        >
                            정말 삭제하시겠습니까?
                        </v-card-title>

                        <!--                                <v-card-text>-->
                        <!--                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.-->
                        <!--                                </v-card-text>-->

                        <v-divider></v-divider>

                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="primary"
                                   text @click="commentRemove">Yes
                            </v-btn>
                            <v-btn color="primary"
                                   text @click="no">No
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </div>
        </template>
    </div>
</template>


<script>
    import CommentForm from "~/components/CommentForm";

    export default {
        components: {CommentForm},
        props: {
            post: {
                type: Object,
                required: true,
            },

        },
        data() {
            return {
                fav: false,
                commentOpened: false,
                visible: false,
                dialog: false,
                commentdialog: false,
                commentsId: '',
            };
        },

        computed: {
            me() {
                return this.$store.state.user.me;
            },
            authtoken() {
                return this.$store.state.user.authtoken;
            },
            heartIcon() {
                return this.fav ? 'mdi-heart' : 'mdi-heart-outline';
            },
            // liked() {
            //     const me = this.$store.state.users.me;
            //     return !!(this.post.Likers || []).find(v => v.id === (me && me.id));
            // },
        },
        methods: {
            onClickHeart() {
                if (!this.me) {
                    return alert('로그인이 필요합니다.');
                }
            },
            ok() {
                this.$store.dispatch('posts/remove', {
                    postId: this.post.postId,
                });
                this.dialog = false;
            },
            no() {
                this.dialog = false;
            },
            openConfirm() {
                this.dialog = true;

            },
            openCommentConfirm(commentsId) {
                this.commentdialog = true;
                this.commentsId = commentsId;
            },
            commentRemove() {
                // console.log(this.post.Comments.commentsId);
                this.$store.dispatch(`posts/removeComment`, {
                    commentsId: this.commentsId,
                    postId: this.post.postId,
                });
                this.commentdialog = false;
            },
            onRemovePost() {
                this.$store.dispatch('posts/remove', {
                    postId: this.post.postId,
                });
            },
            onEditPost() {

            },
            onToggleComment() {
                this.commentOpened = !this.commentOpened
                if (this.commentOpened) {
                    this.$store.dispatch('posts/loadComments', {
                        postId: this.post.postId,
                    })
                        .then((res) => {
                            console.log("loadComments success")
                        })
                        .catch((err) => {
                            console.log("loadComments err")
                        })
                }

            }
        }
    }
</script>

<style scoped>
    a {
        color: inherit;
        text-decoration: none;
    }

    .comment {
        background-color: rgba(0, 0, 0, 0.04)
    }
</style>
