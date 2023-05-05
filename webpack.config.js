import MiniCssExtractPlugin from 'mini-css-extract-plugin'
import path from 'path'
import fs from 'fs'
import {fileURLToPath} from 'url'

const dirname = path.dirname(fileURLToPath(import.meta.url))
const entries = {}

fs.readdirSync('./src/main/ts/')
    .filter((fileName) => fileName.match(/.*\.ts$/))
    .forEach((fileName) => {
        entries[fileName.replace(/\.ts$/, '')] = [`./src/main/ts/${fileName}`]
    })

const config = {
    entry: entries,
    output: {
        filename: 'bundle-[name].js',
        path: path.resolve(dirname, 'src/main/resources/static/js'),
        clean: true
    }
    ,resolve: {
        extensions: ['.ts', '.js']
    },
    devtool: 'source-map',
    mode: 'development',
    plugins: [
        new MiniCssExtractPlugin({
            filename: '../css/bundle-[name].css'
        })
    ],
    module: {
        rules: [
            {
                test: /\.s?css$/i,
                use: [
                    MiniCssExtractPlugin.loader,
                    'css-loader',
                    'sass-loader'
                ]
            },
            {
                test: /\.ts(x?)$/,
                exclude: /node_modules/,
                use: [
                    {
                        loader: 'ts-loader'
                    }
                ]
            },
            {
                test: /\.(png|svg|jpe?g|gif)$/i,
                type: 'asset'
            },
            {
                test: /\.(woff2?|eot|ttf|otf)$/i,
                type: 'asset'
            }
        ]
    },
    devServer: {
        hot: true,
        open: true,
        port: 8081,
        static: {
            directory: path.resolve(dirname, 'src/main/resources/static')
        }
    },
    experiments: {
        topLevelAwait: true
    }
}

export default config
