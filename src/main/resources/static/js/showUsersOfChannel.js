/**
 *
 * @type {NodeListOf<HTMLTableRowElement>}
 */
const allUserRows = document.querySelectorAll("tbody tr td");
const channelDetails = document.querySelector("article.card");

/**
 *
 * @param id {number}
 * @returns {Promise<void>}
 */
async function fetchUsersOfChannel(id) {
	while (channelDetails.firstChild) {
		channelDetails.removeChild(channelDetails.firstChild);
	}

	const res = await fetch(`/api/channels/${id}/users`);
	if (!res.ok) return;

	if (res.status === 204) {
		channelDetails.innerHTML = `
		<div class="card-body bg-dark text-light">
			<h5 class="card-title">Users of channel</h5>
			No users in this channel
		</div>
		`;
		channelDetails.classList.remove("visually-hidden");
		return;
	}

	/**
	 *
	 * @type {{userID: number, name: string, birthdate: Date, role: string}[]}
	 */
	const json = await res.json();

	channelDetails.innerHTML = `
		<div class="card-body bg-dark text-light">
			<h5 class="card-title">Users of channel</h5>
			<ul class="list-group list-group-flush">
				${json.map(user => `
					<li class="list-group-item bg-dark text-light">
						<a href="/users/${user.userID}">${user.name}</a>
						<span class="badge bg-primary rounded-pill">${user.role}</span>
						<span class="badge bg-secondary rounded-pill">${user.birthdate}</span>
					</li>
				`).join("")}
			</ul>
		</div>
	`;

	channelDetails.classList.remove("visually-hidden");

}

/**
 *
 * @param e {Event}
 */
async function toggleRow(e) {
	if (e.target.tagName.toLocaleLowerCase() === "a" || e.target.tagName.toLocaleLowerCase() === "button") return;

	const clickedRow = e.target.closest("tr");
	const activeRow = document.querySelector("tbody tr.table-active");
	if (activeRow) activeRow.classList.toggle("table-active");
	if (activeRow !== clickedRow) {
		clickedRow.classList.toggle("table-active");
		const rowID = clickedRow.id;
		const id = +rowID.substring(rowID.indexOf("_") + 1);
		await fetchUsersOfChannel(id);
	} else {
		channelDetails.classList.add("visually-hidden");
	}
}

allUserRows.forEach(row => row.addEventListener("click", toggleRow));
