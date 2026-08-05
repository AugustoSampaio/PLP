export interface WorkspaceExample {
  markdown: string;
  code: string;
}

const WORKSPACE_EXAMPLES: Record<string, WorkspaceExample> = {
  Exp1: {
    markdown: "# Expressoes 1\n\nExample notebook for the first expressions language.",
    code: 'length "abcd" + 6',
  },
  Exp2: {
    markdown: "# Expressoes 2\n\nExample notebook for the second expressions language.",
    code: `let var a = 3 in 
    let var a = 2, var b = a
        in a+b`,
  },
  Func1: {
    markdown: "# Funcional 1\n\nExample notebook for the first functional language.",
    code: `let fun fat n =
        let fun mult x y = if (x == 0) then (0) else (y + (mult((x - 1),y)))
        in if (n == 0) then (1) else (mult(n,(fat (n - 1))))
    in fat(5)`,
  },
  Func2: {
    markdown: "# Funcional 2\n\nExample notebook for the second functional language.",
    code: `(let fun dec n = if (n==0) then 0 else n + dec(n-1) in dec)(5)`,
  },
  Func3: {
    markdown: "# Funcional 3\n\nExample notebook for the third functional language.",
    code: `let fun positivo x = x > 0 in
    (let fun filter p xxs =
        if xxs == [] then [] 
        else let var x = head xxs, var xs = tail xxs in
        (if p(x) then x : filter(p, xs)
        else filter(p,xs)) in filter)(positivo, [1,-1, 0,-3,2,3,4])`,
  },
  Imp1: {
    markdown: "# Imperativa 1\n\nExample notebook for the first imperative language.",
    code: `{
  var a = 3,
  var c = 0;
  read(c);
  write(a);
  write(c);
  { var a = 2,
    var b = 5,
    var c = false,
    var d = "oi";
    read(c);
    write(a);
    write(b+a);
    write(c);
    write(d)
  };
  write(a)
}`,
  },
  Imp2: {
    markdown: "# Imperativa 2\n\nExample notebook for the second imperative language.",
    code: `{
    var a  =  1 ,
    proc incA (int z)  {
      a := a + z
    };
    call incA(3);
    call incA(5);
    write(a)
}`,
  },
  OO1: {
    markdown: "# Objetos 1\n\nExample notebook for the first object-oriented language.",
    code: `{
      classe Contador {
          int valor = 10;
          proc print() {
            write(this.valor)
          }
      }
      ;

      {
        Contador c := new Contador
        ;
        write("Teste do write");
        c.print()
     }

   }`,
  },
  OO2: {
    markdown: "# Objetos 2\n\nExample notebook for the second object-oriented language.",
    code: `{
    classe Eletrodomestico{
      boolean ligado = false,
          int voltagem = 220;

          Eletrodomestico(boolean ligado, int voltagem){
            this.ligado := ligado;
            this.voltagem := voltagem
    },
    proc ligar(){
      this.ligado := true
      },
      proc desligar(){
            this.ligado := false
      },
    proc imprimeEstado(){
            write("Ligado: " ++ this.ligado);
            write("Voltagem: " ++ this.voltagem)
      }
    };
    {
      Eletrodomestico eletro := new Eletrodomestico(false,110);
      eletro.imprimeEstado();
      eletro.ligar();
      eletro.imprimeEstado()
    }
}`,
  },
};

  export default WORKSPACE_EXAMPLES;