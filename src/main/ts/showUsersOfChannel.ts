import {type User} from './modules/types'

// eslint-disable-next-line no-undef
const allUserRows: NodeListOf<HTMLTableRowElement> = document.querySelectorAll('tbody tr td')
const channelDetails = document.querySelector('article.card')

async function fetchUsersOfChannel(id: number) {
    while (channelDetails?.firstChild) {
        channelDetails.removeChild(channelDetails.firstChild)
    }

    const res = await fetch(`/api/channels/${id}/users`)
    if (!res.ok) return

    if (res.status === 204) {
        if (!channelDetails) return
        channelDetails.innerHTML = `
		<div class="card-body bg-dark text-light">
			<h5 class="card-title">Users of channel</h5>
			No users in this channel
		</div>
		`
        channelDetails.classList.remove('visually-hidden')
        return
    }

    const data: User[] = await res.json()

    if (!channelDetails) return
    channelDetails.innerHTML = `
		<div class="card-body bg-dark text-light">
			<h5 class="card-title">Users of channel</h5>
			<ul class="list-group list-group-flush">
				${data.map(user => `
					<li class="list-group-item bg-dark text-light">
						<a href="/users/${user.userID}">${user.name}</a>
						<span class="badge bg-primary rounded-pill">${user.role}</span>
						<span class="badge bg-secondary rounded-pill">${user.birthdate}</span>
					</li>
				`).join('')}
			</ul>
		</div>
	`

    channelDetails?.classList.remove('visually-hidden')

}

async function toggleRow(e: Event) {
    if (( e.target as HTMLLinkElement ).tagName.toLocaleLowerCase() === 'a' || ( e.target as HTMLButtonElement).tagName.toLocaleLowerCase() === 'button') return

    const clickedRow = ( e.target as HTMLElement ).closest('tr')
    const activeRow = document.querySelector('tbody tr.table-active')
    if (activeRow) activeRow.classList.toggle('table-active')
    if (activeRow !== clickedRow) {
        clickedRow?.classList.toggle('table-active')
        const rowID = clickedRow?.id
        if (!rowID) return
        const id = +rowID.substring(rowID.indexOf('_') + 1)
        await fetchUsersOfChannel(id)
    } else {
        channelDetails?.classList.add('visually-hidden')
    }
}

allUserRows.forEach(row => row.addEventListener('click', toggleRow))
