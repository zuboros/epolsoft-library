export const noWhiteSpace = {
   validator: (_, value) =>
      !value.includes("  ")
         ? Promise.resolve()
         : Promise.reject(new Error("No spaces allowed"))
}