import csrfHeader from './csrfHeader'
import {createToast} from '../deleteEntity'
import {Toast} from 'bootstrap'

export async function deletePost(button: HTMLButtonElement) {
    const id = parseInt( button.dataset.postId ?? '')
    const {name, value} = csrfHeader()
    const res = await fetch(`/api/posts/${id}`, {
        method: 'DELETE'
        , headers: {
            [name]: value
        }
    })

    if (res.status === 204) {
        const {toastContainer, toastElement} = createToast(id)
        if (!toastContainer) return
        toastElement.innerHTML = `
			<div class="toast-header text-success">
				<i class="bi bi-bell-fill me-2"></i>
				<strong class="me-auto">Success</strong>
				<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
			</div>
			<div class="toast-body">
				Post with id ${id} was deleted.	
			</div>`

        toastContainer.appendChild(toastElement)
        const toast = new Toast(toastElement, {delay: 3000})
        toast.isShown() || toast.show()
        button.closest('.post')?.remove()
    }

    if (res.status === 403) {
        const {toastContainer, toastElement} = createToast(id)
        if (!toastContainer) return
        toastElement.innerHTML = `
			<div class="toast-header text-danger">
				<i class="bi bi-bell-fill me-2"></i>
				<strong class="me-auto">Error</strong>
				<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
			</div>
			<div class="toast-body">
				You don't have permission to delete post with id ${id}.
			</div>
		`

        toastContainer.appendChild(toastElement)
        const toast = new Toast(toastElement)
        toast.isShown() || toast.show()
    }

    if (res.status === 404) {
        const {toastContainer, toastElement} = createToast(id)
        if (!toastContainer) return
        toastElement.innerHTML = `
			<div class="toast-header text-danger">
				<i class="bi bi-bell-fill me-2"></i>
				<strong class="me-auto">Error</strong>
				<button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
			</div>
			<div class="toast-body">
				Something went wrong while deleting post with id ${id}.
			</div>
		`

        toastContainer.appendChild(toastElement)
        const toast = new Toast(toastElement)
        toast.isShown() || toast.show()
    }
}
