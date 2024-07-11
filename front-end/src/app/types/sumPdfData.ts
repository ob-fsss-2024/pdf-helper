import {ResponseMode} from './responseMode'
export interface SumPdfData {
    pdf: FormData,
    mode: ResponseMode
    wordLimit: number
}
