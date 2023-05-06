// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import {z} from 'zod'

export const role = z.enum(['ADMIN', 'MOD', 'USER'])

export const UserSchema = z.object({
    userID: z.number().int().positive(),
    name: z.string().min(1).max(255).trim(),
    birthdate: z.coerce.date(),
    role: role
})

export type User = z.infer<typeof UserSchema>

export const PostSchema = z.object({
    postID: z.number().int().positive(),
    content: z.string().min(1).max(255).trim().toLowerCase(),
    username: z.string().min(1).max(255).trim(),
    userID: z.number().int().positive(),
    upVotes: z.number().int(),
    postedAt: z.coerce.date()
})

export type Post = z.infer<typeof PostSchema>
