attribute vec3 vPosition;
attribute vec2 vCoord;
uniform mat4 uMVPMatrix;
varying vec2 textureCoordinate;

void main(){
    gl_Position = uMVPMatrix*vec4(vPosition,200.0);
    textureCoordinate = vCoord;
}