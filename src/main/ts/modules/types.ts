export interface Post {
    postID: number,
    content: string,
    username:string,
    userID: number,
    upVotes: number,
    postedAt: string
}

export interface User {
    userID: number,
    name: string,
    birthdate: Date,
    role: string
}
