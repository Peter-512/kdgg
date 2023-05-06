export const checkAuth = async ()  => {
    const res = await fetch('http://localhost:8081/api/authenticated')
    if (res.status === 200) {
        const data = await res.json()
        return data as Promise<boolean>
    }
}
