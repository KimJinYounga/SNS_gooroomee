export default function ( {store, redirect} ) {
    if(!store.state.user.me) {
        redirect('/');
    }
}