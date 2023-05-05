export default () => {
    const cookieValue = document.cookie
        .split(';')
        .map(cookieEntry => {
			 const array = cookieEntry.trim().split('=').map(string => string.trim())
            const cookieName = array[0]
            const cookieValue = array[1]
            return {name: cookieName, value: cookieValue}
        })
        .find(cookieObject => cookieObject.name === 'XSRF-TOKEN')
    if (!cookieValue) throw Error('XSRF-TOKEN not found in cookie')
    const {name, value} = cookieValue
    return {name: `X-${name}`, value}
}
