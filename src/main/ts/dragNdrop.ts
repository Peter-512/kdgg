import dragula from 'dragula'

const channels = document.querySelector('#channels')
if (channels)
    dragula([channels])

const posts = document.querySelector('#posts')
if (posts)
    dragula([posts])

const users = document.querySelector('#users')
if (users)
    dragula([users])
