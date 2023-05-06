import {deletePost} from './modules/deletePost'

// eslint-disable-next-line no-undef
const deleteButtons: NodeListOf<HTMLButtonElement>  = document.querySelectorAll('.delete')
const posts = document.querySelectorAll('.post')


deleteButtons.length && posts.forEach(post => {
    post.addEventListener('mouseover', () => {
        post.children[1].classList.remove('visually-hidden')
    })
    post.addEventListener('mouseout', () => {
        post.children[1].classList.add('visually-hidden')
    })
})

deleteButtons?.forEach(button => {
    button.addEventListener('click', () => deletePost(button))
})
