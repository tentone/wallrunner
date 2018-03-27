//Solarize Ambient Pixel Shader
//Tentone 2014
//precision mediump float;

varying vec4 vColor;
varying vec2 vTexCoord;

uniform sampler2D u_texture;
uniform vec4 ambientColor;

void main()
{
	vec4 diffuseColor = texture2D(u_texture, vTexCoord);
	
	gl_FragColor = vec4(vColor.rgb*ambientColor.rgb*ambientColor.a+diffuseColor.rgb,diffuseColor.a);
}