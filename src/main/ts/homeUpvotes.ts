import {vote} from './vote'

document.querySelectorAll('.upvote, .downvote').forEach(button => button.addEventListener('click', vote))
