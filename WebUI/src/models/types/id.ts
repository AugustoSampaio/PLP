export type ID = string & { readonly __brand: "ID" };

export function createID(): ID {
  return `${Date.now()}-${Math.random().toString(36).substring(2, 9)}` as ID;
}
