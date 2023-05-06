import {vote} from './vote'
import csrfHeader from './modules/csrfHeader'
import {deletePost} from './modules/deletePost'
import {type Post, PostSchema} from './modules/types'
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {z, ZodError} from 'zod'

const addPostForm: HTMLFormElement | null = document.querySelector('#add-post-form')
const submitButton = document.querySelector('#send-message')
const messageContent: HTMLInputElement | null = document.querySelector('#message-input')
const bodyElement = document.querySelector('body')
const role = bodyElement?.dataset.role
const channelID = parseInt( bodyElement?.dataset.channelId ?? '')

if (!addPostForm || !messageContent || !bodyElement || !channelID)  throw new Error('Element not found')

addPostForm.onsubmit = (event) => event.preventDefault()

const schema = z.object({
    content: z.string().min(1).max(255).trim().toLowerCase(),
    channelID: z.number().int().positive()
})

async function postMessage() {
    if (!addPostForm || !messageContent || !bodyElement || !channelID)  return
    const isValid = addPostForm.checkValidity()
    addPostForm.classList.add('was-validated')

    if (!isValid) return

    const content = messageContent.value
    const url = '/api/posts'
    const body = {content, channelID}

    let parsedBody
    try {
        parsedBody = schema.parse(body)
    } catch (e: ZodError) {
        messageContent.classList.remove('is-valid')
        messageContent.classList.add('is-invalid')
        const invalidFeedbackElement: HTMLDivElement | null = document.querySelector('.invalid-feedback')
        if (!invalidFeedbackElement) throw new Error('Element not found')
        invalidFeedbackElement.innerText = e.errors[0].message
        return
    }

    const {name, value} = csrfHeader()

    const res = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [name]: value
        },
        body: JSON.stringify(parsedBody)
    })

    if (res.status === 404) {
        return alert('Channel not found')
    }

    if (res.status === 400) {
        return alert('Invalid message')
    }

    const data: Post = await res.json()
    const parsedData = PostSchema.parse(data)
    const {postID,
        content: message,
        username,
        userID,
        upVotes,
        postedAt} = parsedData

    let formattedDate
    const timeDiff = Date.now() - postedAt
    const oneDay = 1000 * 60 * 60 * 24
    const timeString = postedAt.toLocaleTimeString('en-US', {
        hour: 'numeric',
        minute: 'numeric',
        hour12: false
    })
    if (timeDiff < oneDay) {
        formattedDate = `today at ${timeString}`
    } else if (timeDiff < oneDay * 2) {
        formattedDate = `yesterday at ${timeString}`
    } else {
        formattedDate = postedAt.toLocaleString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
            hour12: false
        })
    }

    const post = document.createElement('article')
    post.classList.add('d-flex', 'py-2', 'justify-content-between', 'post')
    post.innerHTML = `
			<div class="d-flex">
				<div class="me-2 d-flex flex-md-row flex-column align-items-center justify-content-md-around"
 					style="width: 50px"
 					id="post_${postID}">
					<button class="btn p-0 upvote" type="submit">
						<i class="bi bi-arrow-up text-success"></i>
					</button>
					<span class="text-warning">${upVotes}</span>
					<button class="btn p-0 downvote">
						<i class="bi bi-arrow-down text-danger"></i>
					</button>
				</div>
				<div style="width: calc(100% - 50px)">
					<a class="text-decoration-none" href="/users/${userID}">
						${username}
					</a>
					<span class="text-secondary">
						${formattedDate}
					</span>
					<p class="m-0">
						${message}
					</p>
				</div>
			</div>
				${role === '[ROLE_MOD]' || role === '[ROLE_ADMIN]' ? '<button class=\'btn btn-outline-danger visually-hidden delete\'>' +
		'<i class=\'bi bi-trash-fill\'></i>' +
		'</button>' : ''}
	`

    const deleteButton: HTMLButtonElement | null = post.querySelector('.delete')
    if (deleteButton) {
        deleteButton.dataset.postId = `${postID}`
        deleteButton.addEventListener('click', async () => {
            await deletePost(deleteButton)
        })

        post.addEventListener('mouseover', () => {
            deleteButton.classList.remove('visually-hidden')
        })
        post.addEventListener('mouseout', () => {
            deleteButton.classList.add('visually-hidden')
        })
    }

    const postList = document.querySelector('#posts')
    postList?.append(post)

    document.querySelectorAll('.upvote, .downvote').forEach(button => button.addEventListener('click', vote))

    addPostForm.reset()
    addPostForm.classList.remove('was-validated')
    messageContent.blur()
}


submitButton?.addEventListener('click', postMessage)

messageContent.addEventListener('keyup', () => {
    const body = {content: messageContent.value, channelID}
    try {
        schema.parse(body)
        messageContent.classList.remove('is-invalid')
        messageContent.classList.add('is-valid')
    } catch (e: ZodError) {
        messageContent.classList.remove('is-valid')
        messageContent.classList.add('is-invalid')
        const invalidFeedbackElement: HTMLDivElement | null = document.querySelector('.invalid-feedback')
        if (!invalidFeedbackElement) throw new Error('Element not found')
        invalidFeedbackElement.innerText = e.errors[0].message
        return
    }
})

window.addEventListener('keydown', (event) => {
    messageContent.focus()

    if (event.key === 'Escape') {
        messageContent.blur()
        messageContent.classList.remove('is-invalid')
        addPostForm.classList.remove('was-validated')
        return
    }

    if (event.key === 'Enter') return postMessage()
})

document.querySelectorAll('.upvote, .downvote').forEach(button => button.addEventListener('click', vote))
