{
  description = "A very basic flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
  };

  outputs =
    { self, nixpkgs }:
    let
      pkgs = nixpkgs.legacyPackages.x86_64-linux;
    in
    {
      formatter.x86_64-linux = pkgs.nixfmt-rfc-style;
      devShells.x86_64-linux.default = pkgs.mkShell {
        packages = [ 
          pkgs.graalvmPackages.graalvm-oracle 
        ];
      };
    };
}
