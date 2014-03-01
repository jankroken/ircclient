package com.github.jankroken.ircclient.javafx

object InputModifier {
  def replaceSymbols(s:String) = replaceGreekLetters(replaceArrows(replaceMath(replaceUnits(s))))

  def replaceGreekLetters(s:String) = {
     s.replaceAll("::alpha","α")
      .replaceAll("::beta","β")
      .replaceAll("::gamma","γ")
      .replaceAll("::Gamma","Γ")
      .replaceAll("::delta","δ")
      .replaceAll("::Delta","Δ")
      .replaceAll("::epsilon","ε")
      .replaceAll("::zeta","ζ")
      .replaceAll("::eta","η")
      .replaceAll("::theta","θ")
      .replaceAll("::Theta","Θ")
      .replaceAll("::iota","ι")
      .replaceAll("::kappa","κ")
      .replaceAll("::lambda","λ")
      .replaceAll("::Lambda","Λ")
      .replaceAll("::mu","μ")
      .replaceAll("::nu","ν")
      .replaceAll("::xi","ξ")
      .replaceAll("::Xi","Ξ")
      .replaceAll("::pi","π")
      .replaceAll("::Pi","Π")
      .replaceAll("::rho","ρ")
      .replaceAll("::sigma","σ")
      .replaceAll("::Sigma","Σ")
      .replaceAll("::tau","τ")
      .replaceAll("::upsilon","υ")
      .replaceAll("::phi","φ")
      .replaceAll("::Phi","Φ")
      .replaceAll("::chi","χ")
      .replaceAll("::Chi","Χ")
      .replaceAll("::psi","ψ")
      .replaceAll("::Psi","Ψ")
      .replaceAll("::omega","ω")
      .replaceAll("::Omega","Ω")
  }

  def replaceArrows(s:String) =
    s.replaceAll("::\\+>>","⤀")
    .replaceAll("::\\+\\+>>","⤁")
    .replaceAll("::<=\\|=","⤂")
    .replaceAll("::=\\|=>","⤃")
    .replaceAll("::<=\\|=>","⤄")
    .replaceAll("::\\|->>","⤅")
    .replaceAll("::<=\\|","⤆")
    .replaceAll("::\\|=>","⤇")
    .replaceAll("::\\+v","⤈")
    .replaceAll("::\\+^","⤉")
    .replaceAll("::\\|\\|\\|^","⤊")
    .replaceAll("::\\|\\|\\|v","⤋")
    .replaceAll("\\|^-","⤒")
    .replaceAll("\\|v_","⤓")
    .replaceAll("::\\\\-\\\\","⥋")
    .replaceAll("::/-/","⥊")
    .replaceAll("::~~->","⥵")
    .replaceAll("::<=>","⬄")
    .replaceAll("::<--","⟵")
    .replaceAll("::-->","⟶")
    .replaceAll("::<-->","⟷")
    .replaceAll("::<==","⟸")
    .replaceAll("::==>","⟹")
    .replaceAll("::<==>","⟺")
    .replaceAll("::<--\\|","⟻")
    .replaceAll("::\\|-->","⟼")
    .replaceAll("::<==\\|","⟽")
    .replaceAll("::\\|==>","⟾")
    .replaceAll("::<-","←")
    .replaceAll("::\\|^","↑")
    .replaceAll("::->","→")
    .replaceAll("::\\|v","↓")
    .replaceAll("::<->","↔")
    .replaceAll("::^\\|v","↕")
    .replaceAll("::<-/-","↚")
    .replaceAll("::-/->","↛")
    .replaceAll("::<~","↜")
    .replaceAll("::~>","↝")
    .replaceAll("::<-/->","↮")
    .replaceAll("::<=/=","⇍")
    .replaceAll("::<=/=>","⇎")
    .replaceAll("::=/=>","⇏")
    .replaceAll("::<=","⇐")
    .replaceAll("::=>","⇒")
    .replaceAll("::<=>","⇔")

  // should add more

  def replaceMath(s:String) =
    s.replaceAll("::not","¬")
    .replaceAll("::\\+-","±")
    .replaceAll("::pow1","¹")
    .replaceAll("::pow2","²")
    .replaceAll("::pow3","³")
    .replaceAll("::micro","µ")
    .replaceAll("::middot","·")
    .replaceAll("::1/4","¼")
    .replaceAll("::1/2","½")
    .replaceAll("::3/4","¾")
    .replaceAll("::\\?","¿")
    .replaceAll("::crossprod","×")
    .replaceAll("::div","÷")
    .replaceAll("::exist","Ǝ")
    .replaceAll("::\\|=","ǂ")
    .replaceAll("::/=","≠")
    .replaceAll("::~~","≈")
    .replaceAll("::revepsilon","ɜ")
    .replaceAll("::\\|~\\|","⨅")
    .replaceAll("::\\|_\\|","⨆")
    .replaceAll("::=<","≤")
    .replaceAll("::>=","≥")
    .replaceAll("::subset","⸦")
    .replaceAll("::superset","⸧")
    .replaceAll("::_\\|_","⟂")
    .replaceAll("::<<","⟪")
    .replaceAll("::>>","⟫")
    .replaceAll("::\\(\\(","⸨")
    .replaceAll("::\\)\\)","⸩")
    .replaceAll("::all","∀")
    .replaceAll("::empty","∅")
    .replaceAll("::\\+-","∓")
    .replaceAll("::and","∧")
    .replaceAll("::or","∨")
    .replaceAll("::intersection","∩")
    .replaceAll("::union","∪")
    .replaceAll("::~=","≈")
    .replaceAll("::\\|-","⊢")
    .replaceAll("::\\[\\[","⟦")
    .replaceAll("::\\]\\]","⟧")
    .replaceAll("::integers","ℤ")
    .replaceAll("::reals","ℝ")
    .replaceAll("::rationals","ℚ")
    .replaceAll("::naturals","ℕ")
    .replaceAll("::complex","ℂ")
    .replaceAll("::elementof","∈")
    .replaceAll("::notexist","∄")
    .replaceAll("::/E","∄")
    .replaceAll("::2","²")


  def replaceUnits(s:String) = s
    .replaceAll("::celsius","℃")

}
