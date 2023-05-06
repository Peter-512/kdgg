import {checkAuth} from './modules/checkAuth'
import 'bubbly-bg'

const isAuthenticated = await checkAuth()

if (!isAuthenticated) {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    // eslint-disable-next-line no-undef
    bubbly({
        colorStart: '#081e03',
        colorStop: '#442651',
        blur: 5,
        compose: 'source-over',
        bubbleFunc: () => `hsla(140, ${Math.random() * 50 + 50}%, ${Math.random() * 50}%)`
    })
}
