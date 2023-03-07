import {vote} from "./vote.js";

document.querySelectorAll(`.upvote, .downvote`).forEach(button => button.addEventListener("click", vote));
