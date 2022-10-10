const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');

module.exports = {
	mode: "development",
	entry: './src/index.tsx',
	devtool: 'inline-source-map',
	module: {
		rules: [
			{
				test: /\.css$/i,
				use: [MiniCssExtractPlugin.loader, "css-loader"],
			},
			{
				test: /\.(js|jsx|tsx|ts)$/,
				exclude: /node_modules/,
				use: [
					'babel-loader',
					'ts-loader', // I WANTED ts-transformer-keys with getCustomTransformers
				],
			},
		],
	},
	resolve: {
		plugins: [new TsconfigPathsPlugin({/* options: see below */})],
		extensions: ['.tsx', '.ts', '.js', '.jsx'],
	},
	plugins: [
		new MiniCssExtractPlugin(),
		new HtmlWebpackPlugin({
			template: "./public/index.html",
			inject: true
		})
	],
	output: {
		path: path.resolve(__dirname, "dist"),
		filename: "bundle.js",
		publicPath: "./",
	},
}
