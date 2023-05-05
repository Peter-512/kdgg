import csrfHeader from './modules/csrfHeader'

/**
 *
 * @param action {string} - "join" or "leave"
 */
function swapButtons(action: string) {
    const joinButton = document.querySelector('#join-channel')
    const leaveButton = document.querySelector('#leave-channel')
    if (!joinButton || !leaveButton) return console.log('Failed to swap buttons')

    if (action === 'join') {
        joinButton.classList.add('visually-hidden')
        leaveButton.classList.remove('visually-hidden')
    } else {
        joinButton.classList.remove('visually-hidden')
        leaveButton.classList.add('visually-hidden')
    }
}

/**
 *
 * @param e {Event}
 * @returns {Promise<boolean>}
 */
const joinOrLeaveChannel = async (e: Event) => {
    const channelId = document.URL.substring(document.URL.lastIndexOf('/') + 1)

    const buttonID = ( e.target as HTMLButtonElement).closest('button')?.id

    const action = buttonID === 'join-channel' ? 'join' : 'leave'

    const url = `/api/channels/${channelId}/${action}`
    const {name, value} = csrfHeader()

    const res = await fetch(url, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            [name]: value
        },
        body: JSON.stringify({channelId})
    })

    if (res.status === 200) {
        console.log(`Successfully ${action === 'join' ? 'joined' : 'left'} channel`)
        swapButtons(action)
        return true
    }

    console.log('Failed to join channel')
}

document.querySelector('#join-channel')?.addEventListener('click', joinOrLeaveChannel)
document.querySelector('#leave-channel')?.addEventListener('click', joinOrLeaveChannel)
