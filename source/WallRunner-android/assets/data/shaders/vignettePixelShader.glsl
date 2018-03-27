//precision mediump float;

varying vec4 v_color;
varying vec2 v_texCoord0;

uniform vec2 u_lightPos;
uniform float u_radius;
uniform float u_near;
uniform float u_dim;
uniform sampler2D u_sampler2D;

void main()
{
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;

    vec2 relativePosition = (u_lightPos - gl_FragCoord.xy) / u_radius;
    float len = length(relativePosition);
    float vignette = smoothstep(.5, u_near, len);
    color.rgb = mix(color.rgb, color.rgb * vec3(vignette, vignette, color.b / 3), u_dim);

    gl_FragColor = color;
}