import csrfHeader from './modules/csrfHeader'

/**
 *
 * @param event {Event}
 */
export async function vote(event: Event) {
    const element = event.target as HTMLDivElement
    if (!element) return
    const id = element.closest('div')?.id
    if (!id) return
    const postID = +id.substring(id.indexOf('_') + 1)

    const upVotesElement = element.closest('div')?.querySelector('span')

    const upVotes = parseInt( upVotesElement?.innerText ?? '' )

    const newUpVotes = element.closest('button')?.classList.contains('upvote') ? upVotes + 1 : upVotes - 1

    const url = `/api/posts/${postID}`

    const {name, value} = csrfHeader()

    const response = await fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [name]: value
        },
        body: JSON.stringify({upVotes: newUpVotes})
    })

    if (response.status === 404) {
        return alert('Post not found')
    }

    if (response.status === 400) {
        return alert('Invalid vote')
    }

    if (!upVotesElement) return
    upVotesElement.innerText = String( newUpVotes )
}
