<template>
    <div style="margin-bottom: 20px">
        <v-card>
            <!--            <v-image/>-->
            <v-card-text>
                <v-list-item>
                    <nuxt-link :to="`/member/`+post.email">
                        <v-list-item-avatar color="teal">
                            <v-img
                                    :src="profileImage"

                            />
                            <!--                            <span>{{ post.email[0] }}</span>-->
                        </v-list-item-avatar>
                    </nuxt-link>
                    <v-list-item-content>

                        <v-list-item-title>
                            <nuxt-link :to="`/member/`+post.email">
                                {{ post.email }}
                            </nuxt-link>
                        </v-list-item-title>
                        <v-list-item-subtitle>{{ post.createdAt}}</v-list-item-subtitle>
                    </v-list-item-content>
                    <div v-if="post.fileCnt>0">
                        <v-icon>mdi-paperclip</v-icon>
                        {{ post.fileCnt }}
                    </div>

                </v-list-item>

                <v-list-item>
                    <v-list-item-content>
                        <v-list-item-title>{{ post.title }}</v-list-item-title><br /><br />
                        <br>
                    </v-list-item-content>
                </v-list-item>
                <div>{{ post.content }}</div>
                <v-list-item  v-if="post.isDeleted">
                    삭제된 게시글 입니다.
                </v-list-item>

            </v-card-text>
            <div v-if="!post.isDeleted">
                <v-card-actions>
                    <v-menu offset-y open-on-hover>
                        <template v-slot:activator="{ on }">
                            <v-btn text color="orange" @click="onClickHeart" v-on="on">
                                <v-icon>{{heartIcon}}</v-icon>
                                {{post.LikesLength}}
                            </v-btn>
                        </template>
                        <div style="background: white">
                            <v-list v-for="l in post.Likes" :key="l.id" style="margin: 0 10px">
                                <span>
                                    <nuxt-link :to="`/member/`+l.email">
                                        {{l.email}}
                                    </nuxt-link>
                                </span>
                            </v-list>
                        </div>
                    </v-menu>
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
            <v-list v-for="c in post.Comments" :key="c.id" style="margin: 10px 0">
                <!--                <div style = "background-color: rgba(0, 0, 0, 0.03)">-->
                <v-list-item>
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
                            <v-btn text color="teal" @click="applyComment(c.commentsId)">답글</v-btn>
                        </div>
                    </v-menu>
                </v-list-item>
                <v-list-item v-for="ch in c.children" :key="c.id" style="margin: 10px 60px">
                    <v-list-item-avatar color="teal">
                        <span>{{ch.email[0]}}</span>
                    </v-list-item-avatar>
                    <v-list-item-content class="comment">
                        <h3>{{ch.email}}</h3>
                        <h6>{{ch.createdAt}} </h6>
                        <div v-if="!ch.isDeleted">{{ch.comments}}</div>
                        <div v-else>삭제된 댓글 입니다.</div>
                    </v-list-item-content>
                    <v-menu offset-y open-on-hover v-if="ch.email === me && !ch.isDeleted">
                        <template v-slot:activator="{ on }">
                            <v-btn text color="teal" v-on="on">
                                <v-icon>mdi-dots-horizontal</v-icon>
                            </v-btn>
                        </template>
                        <div style="background: white">
                            <v-btn dark color="red" @click="openCommentConfirm(ch.commentsId)">삭제</v-btn>
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
                commentOpened: true,
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
            liked() {
                const me = this.$store.state.user.me;
                return !!(this.post.Likes || []).find(v =>  v.email ===  (me))
            },
            heartIcon() {
                return this.liked ? 'mdi-heart' : 'mdi-heart-outline';
            },
            profileImage() {
                try {
                    const profile = this.post._links.profileImage;
                    if (profile !== undefined) {
                        return "http://localhost:8080" + profile.href;
                    }
                } catch (e) {
                    return "https://i.stack.imgur.com/l60Hf.png";
                }
                return "https://i.stack.imgur.com/l60Hf.png";

            }
        },
        mounted() {
            this.$store.dispatch('posts/likeList', {
                postId: this.post.postId,
            });
        },
        methods: {
            onClickHeart() {
                if (!this.me) {
                    return alert('로그인이 필요합니다.');
                }
                if (this.liked) {
                    return this.$store.dispatch('posts/unlikePost', {
                        postId: this.post.postId,
                    });
                }
                return this.$store.dispatch('posts/likePost', {
                    postId: this.post.postId,
                });
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
            applyComment(commentsId) {

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
